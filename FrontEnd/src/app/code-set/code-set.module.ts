import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { ToastModule } from 'ng2-toastr/ng2-toastr';
import { ControlsModule } from 'eds-angular4/dist/controls';
import { CodeSetComponent } from './code-set/code-set.component';
import { DialogsModule, LoggerService } from 'eds-angular4';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CodeSetService } from './code-set/code-set.service';
import { ModuleStateService } from 'eds-angular4/dist/common';
import { CodeSetEditorComponent } from './code-set-editor/code-set-editor.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DialogsModule,
    NgbModule,
    ToastModule.forRoot(),
    ControlsModule
  ],
  declarations: [CodeSetComponent, CodeSetEditorComponent],
  entryComponents: [CodeSetEditorComponent],
  providers: [CodeSetService, LoggerService, ModuleStateService]
})
export class CodeSetModule { }
