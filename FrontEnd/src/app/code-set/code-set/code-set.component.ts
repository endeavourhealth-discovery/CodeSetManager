import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { LoggerService } from 'eds-angular4';
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
    console.log('here1');
    this.service.getList()
      .subscribe(
        result => {
          this.codeSets = result;
          this.filteredCodeSets = this.codeSets;
        },
      );
    console.log('here2');
  }

  searchExtracts() {
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

  }

  edit(item : CodeSet) {

  }

  delete(item : CodeSet) {

  }
}
