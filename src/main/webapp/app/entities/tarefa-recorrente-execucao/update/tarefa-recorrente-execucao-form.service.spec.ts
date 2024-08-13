import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-recorrente-execucao.test-samples';

import { TarefaRecorrenteExecucaoFormService } from './tarefa-recorrente-execucao-form.service';

describe('TarefaRecorrenteExecucao Form Service', () => {
  let service: TarefaRecorrenteExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaRecorrenteExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaRecorrenteExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            dataEntrega: expect.any(Object),
            dataAgendada: expect.any(Object),
            ordem: expect.any(Object),
            concluida: expect.any(Object),
            mes: expect.any(Object),
            situacaoTarefa: expect.any(Object),
            tarefaRecorrente: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaRecorrenteExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            dataEntrega: expect.any(Object),
            dataAgendada: expect.any(Object),
            ordem: expect.any(Object),
            concluida: expect.any(Object),
            mes: expect.any(Object),
            situacaoTarefa: expect.any(Object),
            tarefaRecorrente: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaRecorrenteExecucao', () => {
      it('should return NewTarefaRecorrenteExecucao for default TarefaRecorrenteExecucao initial value', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup(sampleWithNewData);

        const tarefaRecorrenteExecucao = service.getTarefaRecorrenteExecucao(formGroup) as any;

        expect(tarefaRecorrenteExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaRecorrenteExecucao for empty TarefaRecorrenteExecucao initial value', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup();

        const tarefaRecorrenteExecucao = service.getTarefaRecorrenteExecucao(formGroup) as any;

        expect(tarefaRecorrenteExecucao).toMatchObject({});
      });

      it('should return ITarefaRecorrenteExecucao', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);

        const tarefaRecorrenteExecucao = service.getTarefaRecorrenteExecucao(formGroup) as any;

        expect(tarefaRecorrenteExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaRecorrenteExecucao should not enable id FormControl', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaRecorrenteExecucao should disable id FormControl', () => {
        const formGroup = service.createTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
