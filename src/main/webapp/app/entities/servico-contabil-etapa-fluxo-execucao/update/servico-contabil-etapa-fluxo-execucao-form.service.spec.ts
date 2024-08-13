import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../servico-contabil-etapa-fluxo-execucao.test-samples';

import { ServicoContabilEtapaFluxoExecucaoFormService } from './servico-contabil-etapa-fluxo-execucao-form.service';

describe('ServicoContabilEtapaFluxoExecucao Form Service', () => {
  let service: ServicoContabilEtapaFluxoExecucaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoContabilEtapaFluxoExecucaoFormService);
  });

  describe('Service methods', () => {
    describe('createServicoContabilEtapaFluxoExecucaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordem: expect.any(Object),
            feito: expect.any(Object),
            prazo: expect.any(Object),
            servicoContabil: expect.any(Object),
            etapaFluxoExecucao: expect.any(Object),
          }),
        );
      });

      it('passing IServicoContabilEtapaFluxoExecucao should create a new form with FormGroup', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordem: expect.any(Object),
            feito: expect.any(Object),
            prazo: expect.any(Object),
            servicoContabil: expect.any(Object),
            etapaFluxoExecucao: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicoContabilEtapaFluxoExecucao', () => {
      it('should return NewServicoContabilEtapaFluxoExecucao for default ServicoContabilEtapaFluxoExecucao initial value', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup(sampleWithNewData);

        const servicoContabilEtapaFluxoExecucao = service.getServicoContabilEtapaFluxoExecucao(formGroup) as any;

        expect(servicoContabilEtapaFluxoExecucao).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicoContabilEtapaFluxoExecucao for empty ServicoContabilEtapaFluxoExecucao initial value', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup();

        const servicoContabilEtapaFluxoExecucao = service.getServicoContabilEtapaFluxoExecucao(formGroup) as any;

        expect(servicoContabilEtapaFluxoExecucao).toMatchObject({});
      });

      it('should return IServicoContabilEtapaFluxoExecucao', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup(sampleWithRequiredData);

        const servicoContabilEtapaFluxoExecucao = service.getServicoContabilEtapaFluxoExecucao(formGroup) as any;

        expect(servicoContabilEtapaFluxoExecucao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicoContabilEtapaFluxoExecucao should not enable id FormControl', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicoContabilEtapaFluxoExecucao should disable id FormControl', () => {
        const formGroup = service.createServicoContabilEtapaFluxoExecucaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
