import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { LoggerService, MessageBoxDialog } from 'eds-angular4';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CodeSetService } from './code-set.service';
import { CodeSet } from '../models/CodeSet';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr';
import { ModuleStateService } from 'eds-angular4/dist/common';

@Component({
  selector: 'app-record-viewer',
  templateUrl: './code-set.component.html',
  styleUrls: ['./code-set.component.css']
})
export class CodeSetComponent implements OnInit {

  codeSets: CodeSet[];
  filteredCodeSets: CodeSet[];
  selection: CodeSet;
  searchTerm: string;

  constructor(private modal: NgbModal,
              private log: LoggerService,
              private router: Router,
              private service: CodeSetService,
              private state: ModuleStateService,
              public toastr: ToastsManager, vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    this.service.getList()
      .subscribe(
        result => {
          this.codeSets = result;
          if (this.service.getSelected()) {
            this.selection = this.service.getSelected();
          } else {
            this.selection = this.codeSets[0];
          }
          this.filteredCodeSets = this.codeSets;
        },
      );
  }

  searchCodeSets() {
    this.filteredCodeSets = this.codeSets;
    this.filteredCodeSets = this.filteredCodeSets.filter(
      codeSet => codeSet.codeSetName.toUpperCase().includes(this.searchTerm.toUpperCase()));
    this.selection = this.filteredCodeSets[0];
  }

  clearSearch() {
    this.searchTerm = '';
    this.filteredCodeSets = this.codeSets;
  }

  add() {
    this.state.setState('codeSetEdit', {extract: null, editMode: false});
    this.router.navigate(['codeSetEdit']);
  }

  edit(item : CodeSet) {
    this.service.setSelected(item);
    this.state.setState('codeSetEdit', {extract: item, editMode: true});
    this.router.navigate(['codeSetEdit']);
  }

  delete(item : CodeSet) {
    this.service.setSelected(item);
    MessageBoxDialog.open(this.modal, 'Delete Code Set', 'Are you sure that you want to delete <b>' +
      item.codeSetName + '</b>?', 'Delete Code Set', 'Cancel')
      .result.then(
      () => this.doDelete(item),
      () => this.log.info('Delete cancelled')
    );
  }

  doDelete(item: CodeSet) {
    this.service.delete(item.id)
      .subscribe(
        () => {
          const index = this.codeSets.indexOf(item);
          this.codeSets.splice(index, 1);
          this.log.success('Code Set deleted successfully', item, 'Code Set');
          this.selection = this.codeSets[0];
        },
        (error) => this.log.error('The Code Set could not be deleted. Please try again.', error, 'Delete Code Set')
      );
  }
}
