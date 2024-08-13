import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../etapa-fluxo-modelo.test-samples';

import { EtapaFluxoModeloFormService } from './etapa-fluxo-modelo-form.service';

describe('EtapaFluxoModelo Form Service', () => {
  let service: EtapaFluxoModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EtapaFluxoModeloFormService);
  });

  describe('Service methods', () => {
    describe('createEtapaFluxoModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            fluxoModelo: expect.any(Object),
          }),
        );
      });

      it('passing IEtapaFluxoModelo should create a new form with FormGroup', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ordem: expect.any(Object),
            fluxoModelo: expect.any(Object),
          }),
        );
      });
    });

    describe('getEtapaFluxoModelo', () => {
      it('should return NewEtapaFluxoModelo for default EtapaFluxoModelo initial value', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup(sampleWithNewData);

        const etapaFluxoModelo = service.getEtapaFluxoModelo(formGroup) as any;

        expect(etapaFluxoModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewEtapaFluxoModelo for empty EtapaFluxoModelo initial value', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup();

        const etapaFluxoModelo = service.getEtapaFluxoModelo(formGroup) as any;

        expect(etapaFluxoModelo).toMatchObject({});
      });

      it('should return IEtapaFluxoModelo', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup(sampleWithRequiredData);

        const etapaFluxoModelo = service.getEtapaFluxoModelo(formGroup) as any;

        expect(etapaFluxoModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEtapaFluxoModelo should not enable id FormControl', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEtapaFluxoModelo should disable id FormControl', () => {
        const formGroup = service.createEtapaFluxoModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
