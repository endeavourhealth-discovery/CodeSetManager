import { Component, Input, OnInit, ViewChild, ViewContainerRef } from '@angular/core';
import { Location } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoggerService, MessageBoxDialog } from 'eds-angular4';
import { ModuleStateService } from 'eds-angular4/dist/common';
import { Router } from '@angular/router';
import { ToastsManager } from 'ng2-toastr';
import { CodeSet } from '../models/CodeSet';
import { CodeSetService } from '../code-set/code-set.service';
import {CodeSetCodes} from "../models/CodeSetCodes";

@Component({
  selector: 'app-code-set-editor',
  templateUrl: './code-set-editor.component.html',
  styleUrls: ['./code-set-editor.component.css']
})
export class CodeSetEditorComponent implements OnInit {

  @Input() selection: CodeSet;
  @Input() editMode: boolean;
  @Input() existing: boolean;
  @Input() selfEdit: boolean;
  dialogTitle: string;

  constructor(private log: LoggerService,
              private modal: NgbModal,
              private router: Router,
              private location: Location,
              private state: ModuleStateService,
              protected codeSetService: CodeSetService,
              public toastr: ToastsManager, vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit(): void {
    const screen = this.state.getState('codeSetEdit');
    if (screen == null) {
      this.selection = {} as CodeSet;
      this.router.navigate(['codeSet']);
      return;
    }

    this.selection = Object.assign( [], screen.extract);
    this.editMode = screen.editMode;
    this.existing = screen.existing;
    this.selfEdit = screen.selfEdit;

    if (!this.editMode) {
      this.dialogTitle = 'Add Code Set';

      this.selection = {
        id: 0,
        codeSetName: '',
        codeSetCodes: null,
        read2ConceptIds: '',
        ctv3ConceptIds: '',
        sctConceptIds: '',
      } as CodeSet;
    } else {
      this.dialogTitle = 'Edit Code Set';

      this.selection = {
        id: this.selection.id,
        codeSetName: this.selection.codeSetName,
        codeSetCodes: this.selection.codeSetCodes,
        read2ConceptIds: this.selection.read2ConceptIds,
        ctv3ConceptIds: this.selection.ctv3ConceptIds,
        sctConceptIds: this.selection.sctConceptIds,
      } as CodeSet;
    }
  }

  isEditMode() {
    return this.editMode;
  }

  save(close: boolean) {
    if (this.validateFormInput()) {
      this.codeSetService.saveExtract(this.selection, this.editMode)
        .subscribe(
          (response) => {
            this.selection = response;
            this.codeSetService.setSelected(this.selection);
            if (close) {
              this.close(!close);
            } else {
              this.log.success('Code Set saved', null, this.dialogTitle)
            }
          },
          (error) => this.log.error('Code Set details could not be saved. Please try again.', error, 'Save Code Set details')
        );
    }
  }

  close(withConfirm: boolean) {
    if (withConfirm) {
      MessageBoxDialog.open(this.modal, this.dialogTitle, 'Any unsaved changes will be lost. ' +
          'Do you want to close without saving?', 'Close without saving', 'Continue editing')
        .result.then(
        (result) => this.location.back(),
        (reason) => {
        }
      );
    } else {
      this.location.back();
    }
  }

  validateFormInput() {
    return true;
  }

  remove(codeSetCode: CodeSetCodes) {
    const index = this.selection.codeSetCodes.indexOf(codeSetCode);
    this.selection.codeSetCodes.splice(index, 1);
  }

  add() {
    let codeSetCode = new CodeSetCodes(this.selection.id);
    this.selection.codeSetCodes.push(codeSetCode);
  }
}
