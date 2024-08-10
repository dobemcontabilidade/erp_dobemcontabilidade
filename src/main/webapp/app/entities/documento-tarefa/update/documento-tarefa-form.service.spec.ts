import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../documento-tarefa.test-samples';

import { DocumentoTarefaFormService } from './documento-tarefa-form.service';

describe('DocumentoTarefa Form Service', () => {
  let service: DocumentoTarefaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DocumentoTarefaFormService);
  });

  describe('Service methods', () => {
    describe('createDocumentoTarefaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDocumentoTarefaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            tarefa: expect.any(Object),
          }),
        );
      });

      it('passing IDocumentoTarefa should create a new form with FormGroup', () => {
        const formGroup = service.createDocumentoTarefaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            tarefa: expect.any(Object),
          }),
        );
      });
    });

    describe('getDocumentoTarefa', () => {
      it('should return NewDocumentoTarefa for default DocumentoTarefa initial value', () => {
        const formGroup = service.createDocumentoTarefaFormGroup(sampleWithNewData);

        const documentoTarefa = service.getDocumentoTarefa(formGroup) as any;

        expect(documentoTarefa).toMatchObject(sampleWithNewData);
      });

      it('should return NewDocumentoTarefa for empty DocumentoTarefa initial value', () => {
        const formGroup = service.createDocumentoTarefaFormGroup();

        const documentoTarefa = service.getDocumentoTarefa(formGroup) as any;

        expect(documentoTarefa).toMatchObject({});
      });

      it('should return IDocumentoTarefa', () => {
        const formGroup = service.createDocumentoTarefaFormGroup(sampleWithRequiredData);

        const documentoTarefa = service.getDocumentoTarefa(formGroup) as any;

        expect(documentoTarefa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDocumentoTarefa should not enable id FormControl', () => {
        const formGroup = service.createDocumentoTarefaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDocumentoTarefa should disable id FormControl', () => {
        const formGroup = service.createDocumentoTarefaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
