import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../servico-contabil-etapa-fluxo-modelo.test-samples';

import { ServicoContabilEtapaFluxoModeloFormService } from './servico-contabil-etapa-fluxo-modelo-form.service';

describe('ServicoContabilEtapaFluxoModelo Form Service', () => {
  let service: ServicoContabilEtapaFluxoModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoContabilEtapaFluxoModeloFormService);
  });

  describe('Service methods', () => {
    describe('createServicoContabilEtapaFluxoModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordem: expect.any(Object),
            prazo: expect.any(Object),
            etapaFluxoModelo: expect.any(Object),
            servicoContabil: expect.any(Object),
          }),
        );
      });

      it('passing IServicoContabilEtapaFluxoModelo should create a new form with FormGroup', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ordem: expect.any(Object),
            prazo: expect.any(Object),
            etapaFluxoModelo: expect.any(Object),
            servicoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicoContabilEtapaFluxoModelo', () => {
      it('should return NewServicoContabilEtapaFluxoModelo for default ServicoContabilEtapaFluxoModelo initial value', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup(sampleWithNewData);

        const servicoContabilEtapaFluxoModelo = service.getServicoContabilEtapaFluxoModelo(formGroup) as any;

        expect(servicoContabilEtapaFluxoModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicoContabilEtapaFluxoModelo for empty ServicoContabilEtapaFluxoModelo initial value', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup();

        const servicoContabilEtapaFluxoModelo = service.getServicoContabilEtapaFluxoModelo(formGroup) as any;

        expect(servicoContabilEtapaFluxoModelo).toMatchObject({});
      });

      it('should return IServicoContabilEtapaFluxoModelo', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup(sampleWithRequiredData);

        const servicoContabilEtapaFluxoModelo = service.getServicoContabilEtapaFluxoModelo(formGroup) as any;

        expect(servicoContabilEtapaFluxoModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicoContabilEtapaFluxoModelo should not enable id FormControl', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicoContabilEtapaFluxoModelo should disable id FormControl', () => {
        const formGroup = service.createServicoContabilEtapaFluxoModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
