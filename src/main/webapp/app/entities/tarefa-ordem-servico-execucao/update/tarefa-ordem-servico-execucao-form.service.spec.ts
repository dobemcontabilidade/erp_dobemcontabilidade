import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-ordem-servico-execucao.test-samples';

import { TarefaOrdemServicoExecucaoFormService } from './tarefa-ordem-servico-execucao-form.service';

describe('TarefaOrdemServicoExecucao Form Service', () => {
  let service: TarefaOrdemServicoExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaOrdemServicoExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaOrdemServicoExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            dataEntrega: expect.any(Object),
            dataAgendada: expect.any(Object),
            concluida: expect.any(Object),
            notificarCliente: expect.any(Object),
            mes: expect.any(Object),
            situacaoTarefa: expect.any(Object),
            tarefaOrdemServico: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaOrdemServicoExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            dataEntrega: expect.any(Object),
            dataAgendada: expect.any(Object),
            concluida: expect.any(Object),
            notificarCliente: expect.any(Object),
            mes: expect.any(Object),
            situacaoTarefa: expect.any(Object),
            tarefaOrdemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaOrdemServicoExecucao', () => {
      it('should return NewTarefaOrdemServicoExecucao for default TarefaOrdemServicoExecucao initial value', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup(sampleWithNewData);

        const tarefaOrdemServicoExecucao = service.getTarefaOrdemServicoExecucao(formGroup) as any;

        expect(tarefaOrdemServicoExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaOrdemServicoExecucao for empty TarefaOrdemServicoExecucao initial value', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup();

        const tarefaOrdemServicoExecucao = service.getTarefaOrdemServicoExecucao(formGroup) as any;

        expect(tarefaOrdemServicoExecucao).toMatchObject({});
      });

      it('should return ITarefaOrdemServicoExecucao', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup(sampleWithRequiredData);

        const tarefaOrdemServicoExecucao = service.getTarefaOrdemServicoExecucao(formGroup) as any;

        expect(tarefaOrdemServicoExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaOrdemServicoExecucao should not enable id FormControl', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaOrdemServicoExecucao should disable id FormControl', () => {
        const formGroup = service.createTarefaOrdemServicoExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
