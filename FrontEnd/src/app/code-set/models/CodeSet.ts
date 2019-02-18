import {CodeSetCodes} from './CodeSetCodes';

export class CodeSet {
  id: number;
  codeSetName: string;
  codeSetCodes: CodeSetCodes[];
  read2ConceptIds: string;
  ctv3ConceptIds: string;
  sctConceptIds: string;

  constructor() {
    this.id = 0;
    this.codeSetName = '';
    this.codeSetCodes = null;
    this.read2ConceptIds = '';
    this.ctv3ConceptIds = '';
    this.sctConceptIds = '';
  }
}
