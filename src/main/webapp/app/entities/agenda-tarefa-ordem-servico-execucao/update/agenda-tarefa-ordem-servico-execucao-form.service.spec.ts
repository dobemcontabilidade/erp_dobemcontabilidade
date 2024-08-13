import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../agenda-tarefa-ordem-servico-execucao.test-samples';

import { AgendaTarefaOrdemServicoExecucaoFormService } from './agenda-tarefa-ordem-servico-execucao-form.service';

describe('AgendaTarefaOrdemServicoExecucao Form Service', () => {
  let service: AgendaTarefaOrdemServicoExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgendaTarefaOrdemServicoExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createAgendaTarefaOrdemServicoExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            horaInicio: expect.any(Object),
            horaFim: expect.any(Object),
            diaInteiro: expect.any(Object),
            ativo: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });

      it('passing IAgendaTarefaOrdemServicoExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            horaInicio: expect.any(Object),
            horaFim: expect.any(Object),
            diaInteiro: expect.any(Object),
            ativo: expect.any(Object),
            tarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgendaTarefaOrdemServicoExecucao', () => {
      it('should return NewAgendaTarefaOrdemServicoExecucao for default AgendaTarefaOrdemServicoExecucao initial value', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup(sampleWithNewData);

        const agendaTarefaOrdemServicoExecucao = service.getAgendaTarefaOrdemServicoExecucao(formGroup) as any;

        expect(agendaTarefaOrdemServicoExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgendaTarefaOrdemServicoExecucao for empty AgendaTarefaOrdemServicoExecucao initial value', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup();

        const agendaTarefaOrdemServicoExecucao = service.getAgendaTarefaOrdemServicoExecucao(formGroup) as any;

        expect(agendaTarefaOrdemServicoExecucao).toMatchObject({});
      });

      it('should return IAgendaTarefaOrdemServicoExecucao', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup(sampleWithRequiredData);

        const agendaTarefaOrdemServicoExecucao = service.getAgendaTarefaOrdemServicoExecucao(formGroup) as any;

        expect(agendaTarefaOrdemServicoExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgendaTarefaOrdemServicoExecucao should not enable id FormControl', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgendaTarefaOrdemServicoExecucao should disable id FormControl', () => {
        const formGroup = service.createAgendaTarefaOrdemServicoExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
