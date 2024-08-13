import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../sub-tarefa-recorrente.test-samples';

import { SubTarefaRecorrenteFormService } from './sub-tarefa-recorrente-form.service';

describe('SubTarefaRecorrente Form Service', () => {
  let service: SubTarefaRecorrenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubTarefaRecorrenteFormService);
  });

  describe('Service methods', () => {
    describe('createSubTarefaRecorrenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            concluida: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
          }),
        );
      });

      it('passing ISubTarefaRecorrente should create a new form with FormGroup', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            concluida: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getSubTarefaRecorrente', () => {
      it('should return NewSubTarefaRecorrente for default SubTarefaRecorrente initial value', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup(sampleWithNewData);

        const subTarefaRecorrente = service.getSubTarefaRecorrente(formGroup) as any;

        expect(subTarefaRecorrente).toMatchObject(sampleWithNewData);
      });

      it('should return NewSubTarefaRecorrente for empty SubTarefaRecorrente initial value', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup();

        const subTarefaRecorrente = service.getSubTarefaRecorrente(formGroup) as any;

        expect(subTarefaRecorrente).toMatchObject({});
      });

      it('should return ISubTarefaRecorrente', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup(sampleWithRequiredData);

        const subTarefaRecorrente = service.getSubTarefaRecorrente(formGroup) as any;

        expect(subTarefaRecorrente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISubTarefaRecorrente should not enable id FormControl', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSubTarefaRecorrente should disable id FormControl', () => {
        const formGroup = service.createSubTarefaRecorrenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
