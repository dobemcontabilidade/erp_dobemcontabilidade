import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../etapa-fluxo-execucao.test-samples';

import { EtapaFluxoExecucaoFormService } from './etapa-fluxo-execucao-form.service';

describe('EtapaFluxoExecucao Form Service', () => {
  let service: EtapaFluxoExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EtapaFluxoExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createEtapaFluxoExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            feito: expect.any(Object),
            ordem: expect.any(Object),
            agendada: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });

      it('passing IEtapaFluxoExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            feito: expect.any(Object),
            ordem: expect.any(Object),
            agendada: expect.any(Object),
            ordemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getEtapaFluxoExecucao', () => {
      it('should return NewEtapaFluxoExecucao for default EtapaFluxoExecucao initial value', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup(sampleWithNewData);

        const etapaFluxoExecucao = service.getEtapaFluxoExecucao(formGroup) as any;

        expect(etapaFluxoExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewEtapaFluxoExecucao for empty EtapaFluxoExecucao initial value', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup();

        const etapaFluxoExecucao = service.getEtapaFluxoExecucao(formGroup) as any;

        expect(etapaFluxoExecucao).toMatchObject({});
      });

      it('should return IEtapaFluxoExecucao', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup(sampleWithRequiredData);

        const etapaFluxoExecucao = service.getEtapaFluxoExecucao(formGroup) as any;

        expect(etapaFluxoExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEtapaFluxoExecucao should not enable id FormControl', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEtapaFluxoExecucao should disable id FormControl', () => {
        const formGroup = service.createEtapaFluxoExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
