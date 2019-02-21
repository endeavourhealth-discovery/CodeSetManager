import { Injectable } from '@angular/core';
import { URLSearchParams, Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { CodeSet } from '../models/CodeSet';

@Injectable()
export class CodeSetService {

  codeSet: CodeSet;

  constructor(private http: Http) {
  }

  getList(): Observable<CodeSet[]> {
    return this.http.get('/api/codeSetManager')
      .map((response) => response.json());
  }

  getSelected(): CodeSet {
    return this.codeSet
  }

  setSelected(codeSet: CodeSet) {
    this.codeSet = codeSet;
  }

  delete(id: any): Observable<any> {
    const params = new URLSearchParams();
    params.set('id', id);
    return this.http.delete('api/codeSetManager', { search : params })
      .map((response) => response.text());
  }

  saveExtract(codeSet: CodeSet, editMode: boolean): Observable<CodeSet> {
    this.setSelected(codeSet);
    const params = new URLSearchParams();
    params.set('editMode', editMode ? '1' : '0');
    return this.http.post('api/codeSetManager/codeSet/save', codeSet, {search: params})
      .map((response) => response.json());
  }
}
