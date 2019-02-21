import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeSetEditorComponent } from './code-set-editor.component';

describe('CodeSetEditorComponent', () => {
  let component: CodeSetEditorComponent;
  let fixture: ComponentFixture<CodeSetEditorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeSetEditorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeSetEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
