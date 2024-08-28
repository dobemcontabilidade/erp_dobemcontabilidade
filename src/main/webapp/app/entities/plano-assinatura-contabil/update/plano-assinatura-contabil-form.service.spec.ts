import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-assinatura-contabil.test-samples';

import { PlanoAssinaturaContabilFormService } from './plano-assinatura-contabil-form.service';

describe('PlanoAssinaturaContabil Form Service', () => {
  let service: PlanoAssinaturaContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoAssinaturaContabilFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoAssinaturaContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            adicionalSocio: expect.any(Object),
            adicionalFuncionario: expect.any(Object),
            sociosIsentos: expect.any(Object),
            adicionalFaturamento: expect.any(Object),
            valorBaseFaturamento: expect.any(Object),
            valorBaseAbertura: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });

      it('passing IPlanoAssinaturaContabil should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            adicionalSocio: expect.any(Object),
            adicionalFuncionario: expect.any(Object),
            sociosIsentos: expect.any(Object),
            adicionalFaturamento: expect.any(Object),
            valorBaseFaturamento: expect.any(Object),
            valorBaseAbertura: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlanoAssinaturaContabil', () => {
      it('should return NewPlanoAssinaturaContabil for default PlanoAssinaturaContabil initial value', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup(sampleWithNewData);

        const planoAssinaturaContabil = service.getPlanoAssinaturaContabil(formGroup) as any;

        expect(planoAssinaturaContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoAssinaturaContabil for empty PlanoAssinaturaContabil initial value', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup();

        const planoAssinaturaContabil = service.getPlanoAssinaturaContabil(formGroup) as any;

        expect(planoAssinaturaContabil).toMatchObject({});
      });

      it('should return IPlanoAssinaturaContabil', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup(sampleWithRequiredData);

        const planoAssinaturaContabil = service.getPlanoAssinaturaContabil(formGroup) as any;

        expect(planoAssinaturaContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoAssinaturaContabil should not enable id FormControl', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoAssinaturaContabil should disable id FormControl', () => {
        const formGroup = service.createPlanoAssinaturaContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
