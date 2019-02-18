import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { CodeSet } from '../models/CodeSet';

@Injectable()
export class CodeSetService {

  constructor(private http: Http) {
  }

  getList(): Observable<CodeSet[]> {
    return this.http.get('/api/codeSetManager/get')
      .map((response) => response.json());
  }
}
