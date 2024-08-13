import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-requerido-tarefa-recorrente.test-samples';

import { AnexoRequeridoTarefaRecorrenteFormService } from './anexo-requerido-tarefa-recorrente-form.service';

describe('AnexoRequeridoTarefaRecorrente Form Service', () => {
  let service: AnexoRequeridoTarefaRecorrenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoRequeridoTarefaRecorrenteFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoRequeridoTarefaRecorrenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            anexoRequerido: expect.any(Object),
            tarefaRecorrente: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoRequeridoTarefaRecorrente should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            anexoRequerido: expect.any(Object),
            tarefaRecorrente: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoRequeridoTarefaRecorrente', () => {
      it('should return NewAnexoRequeridoTarefaRecorrente for default AnexoRequeridoTarefaRecorrente initial value', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup(sampleWithNewData);

        const anexoRequeridoTarefaRecorrente = service.getAnexoRequeridoTarefaRecorrente(formGroup) as any;

        expect(anexoRequeridoTarefaRecorrente).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoRequeridoTarefaRecorrente for empty AnexoRequeridoTarefaRecorrente initial value', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup();

        const anexoRequeridoTarefaRecorrente = service.getAnexoRequeridoTarefaRecorrente(formGroup) as any;

        expect(anexoRequeridoTarefaRecorrente).toMatchObject({});
      });

      it('should return IAnexoRequeridoTarefaRecorrente', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup(sampleWithRequiredData);

        const anexoRequeridoTarefaRecorrente = service.getAnexoRequeridoTarefaRecorrente(formGroup) as any;

        expect(anexoRequeridoTarefaRecorrente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoRequeridoTarefaRecorrente should not enable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoRequeridoTarefaRecorrente should disable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoTarefaRecorrenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
