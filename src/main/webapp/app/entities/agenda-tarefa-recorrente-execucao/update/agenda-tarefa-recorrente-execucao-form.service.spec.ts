import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../agenda-tarefa-recorrente-execucao.test-samples';

import { AgendaTarefaRecorrenteExecucaoFormService } from './agenda-tarefa-recorrente-execucao-form.service';

describe('AgendaTarefaRecorrenteExecucao Form Service', () => {
  let service: AgendaTarefaRecorrenteExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgendaTarefaRecorrenteExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createAgendaTarefaRecorrenteExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            horaInicio: expect.any(Object),
            horaFim: expect.any(Object),
            diaInteiro: expect.any(Object),
            comentario: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
          }),
        );
      });

      it('passing IAgendaTarefaRecorrenteExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            horaInicio: expect.any(Object),
            horaFim: expect.any(Object),
            diaInteiro: expect.any(Object),
            comentario: expect.any(Object),
            tarefaRecorrenteExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgendaTarefaRecorrenteExecucao', () => {
      it('should return NewAgendaTarefaRecorrenteExecucao for default AgendaTarefaRecorrenteExecucao initial value', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup(sampleWithNewData);

        const agendaTarefaRecorrenteExecucao = service.getAgendaTarefaRecorrenteExecucao(formGroup) as any;

        expect(agendaTarefaRecorrenteExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgendaTarefaRecorrenteExecucao for empty AgendaTarefaRecorrenteExecucao initial value', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup();

        const agendaTarefaRecorrenteExecucao = service.getAgendaTarefaRecorrenteExecucao(formGroup) as any;

        expect(agendaTarefaRecorrenteExecucao).toMatchObject({});
      });

      it('should return IAgendaTarefaRecorrenteExecucao', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);

        const agendaTarefaRecorrenteExecucao = service.getAgendaTarefaRecorrenteExecucao(formGroup) as any;

        expect(agendaTarefaRecorrenteExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgendaTarefaRecorrenteExecucao should not enable id FormControl', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgendaTarefaRecorrenteExecucao should disable id FormControl', () => {
        const formGroup = service.createAgendaTarefaRecorrenteExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
