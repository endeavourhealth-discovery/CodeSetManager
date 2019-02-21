import { Injectable } from '@angular/core';
import { AbstractMenuProvider } from 'eds-angular4';
import { MenuOption } from 'eds-angular4/dist/layout/models/MenuOption';
import { CodeSetComponent } from './code-set/code-set/code-set.component';
import { Routes } from '@angular/router';
import { CodeSetEditorComponent } from './code-set/code-set-editor/code-set-editor.component';

@Injectable()
export class AppMenuService implements  AbstractMenuProvider {
  static getRoutes(): Routes {
    return [
      { path: '', redirectTo : 'codeSetManager', pathMatch: 'full' }, // Default route
      { path: 'codeSetManager', component: CodeSetComponent },
      { path: 'codeSetEdit', component: CodeSetEditorComponent}
    ];
  }
  getApplicationTitle(): string {
    return 'Code Set Manager';
  }

  getClientId(): string {
    return 'eds-user-manager';
  }

  useUserManagerForRoles(): boolean {
    return false;
  }

  getMenuOptions(): MenuOption[] {
    return [
      {caption: 'Code Set Manager', state: 'codeSetManager', icon: 'fa fa-user', role: 'eds-user-manager:user-manager'},
      {caption: 'Configuration', state: 'config', icon: 'fa fa-cogs', role: 'eds-user-manager:user-manager'},
      {caption: 'Delegation', state: 'config', icon: 'fa fa-group', role: 'eds-user-manager:user-manager'},
      {caption: 'Audit', state: 'config', icon: 'fa fa-list', role: 'eds-user-manager:user-manager'}
    ];
  }
}
