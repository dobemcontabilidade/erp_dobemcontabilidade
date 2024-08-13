import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contador-responsavel-tarefa-recorrente.test-samples';

import { ContadorResponsavelTarefaRecorrenteFormService } from './contador-responsavel-tarefa-recorrente-form.service';

describe('ContadorResponsavelTarefaRecorrente Form Service', () => {
  let service: ContadorResponsavelTarefaRecorrenteFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContadorResponsavelTarefaRecorrenteFormService);
  });

  describe('Service methods', () => {
    describe('createContadorResponsavelTarefaRecorrenteFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            dataRevogacao: expect.any(Object),
            concluida: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IContadorResponsavelTarefaRecorrente should create a new form with FormGroup', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            dataRevogacao: expect.any(Object),
            concluida: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getContadorResponsavelTarefaRecorrente', () => {
      it('should return NewContadorResponsavelTarefaRecorrente for default ContadorResponsavelTarefaRecorrente initial value', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup(sampleWithNewData);

        const contadorResponsavelTarefaRecorrente = service.getContadorResponsavelTarefaRecorrente(formGroup) as any;

        expect(contadorResponsavelTarefaRecorrente).toMatchObject(sampleWithNewData);
      });

      it('should return NewContadorResponsavelTarefaRecorrente for empty ContadorResponsavelTarefaRecorrente initial value', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup();

        const contadorResponsavelTarefaRecorrente = service.getContadorResponsavelTarefaRecorrente(formGroup) as any;

        expect(contadorResponsavelTarefaRecorrente).toMatchObject({});
      });

      it('should return IContadorResponsavelTarefaRecorrente', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup(sampleWithRequiredData);

        const contadorResponsavelTarefaRecorrente = service.getContadorResponsavelTarefaRecorrente(formGroup) as any;

        expect(contadorResponsavelTarefaRecorrente).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContadorResponsavelTarefaRecorrente should not enable id FormControl', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContadorResponsavelTarefaRecorrente should disable id FormControl', () => {
        const formGroup = service.createContadorResponsavelTarefaRecorrenteFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
