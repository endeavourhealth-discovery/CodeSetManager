export class CodeSetCodes {
  codeSetId: number;
  read2ConceptId: string;
  ctv3ConceptId: string;
  sctConceptId: string;

  constructor(id: number) {
    this.codeSetId = id;
    this.read2ConceptId = '';
    this.ctv3ConceptId = '';
    this.sctConceptId = '';
  }
}
