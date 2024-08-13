import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../agenda-contador-config.test-samples';

import { AgendaContadorConfigFormService } from './agenda-contador-config-form.service';

describe('AgendaContadorConfig Form Service', () => {
  let service: AgendaContadorConfigFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgendaContadorConfigFormService);
  });

  describe('Service methods', () => {
    describe('createAgendaContadorConfigFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            tipoVisualizacaoAgendaEnum: expect.any(Object),
            agendaTarefaRecorrenteExecucao: expect.any(Object),
            agendaTarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });

      it('passing IAgendaContadorConfig should create a new form with FormGroup', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            tipoVisualizacaoAgendaEnum: expect.any(Object),
            agendaTarefaRecorrenteExecucao: expect.any(Object),
            agendaTarefaOrdemServicoExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgendaContadorConfig', () => {
      it('should return NewAgendaContadorConfig for default AgendaContadorConfig initial value', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup(sampleWithNewData);

        const agendaContadorConfig = service.getAgendaContadorConfig(formGroup) as any;

        expect(agendaContadorConfig).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgendaContadorConfig for empty AgendaContadorConfig initial value', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup();

        const agendaContadorConfig = service.getAgendaContadorConfig(formGroup) as any;

        expect(agendaContadorConfig).toMatchObject({});
      });

      it('should return IAgendaContadorConfig', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup(sampleWithRequiredData);

        const agendaContadorConfig = service.getAgendaContadorConfig(formGroup) as any;

        expect(agendaContadorConfig).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgendaContadorConfig should not enable id FormControl', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgendaContadorConfig should disable id FormControl', () => {
        const formGroup = service.createAgendaContadorConfigFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
