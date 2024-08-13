import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-contabil.test-samples';

import { PlanoContabilFormService } from './plano-contabil-form.service';

describe('PlanoContabil Form Service', () => {
  let service: PlanoContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoContabilFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoContabilFormGroup();

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

      it('passing IPlanoContabil should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoContabilFormGroup(sampleWithRequiredData);

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

    describe('getPlanoContabil', () => {
      it('should return NewPlanoContabil for default PlanoContabil initial value', () => {
        const formGroup = service.createPlanoContabilFormGroup(sampleWithNewData);

        const planoContabil = service.getPlanoContabil(formGroup) as any;

        expect(planoContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoContabil for empty PlanoContabil initial value', () => {
        const formGroup = service.createPlanoContabilFormGroup();

        const planoContabil = service.getPlanoContabil(formGroup) as any;

        expect(planoContabil).toMatchObject({});
      });

      it('should return IPlanoContabil', () => {
        const formGroup = service.createPlanoContabilFormGroup(sampleWithRequiredData);

        const planoContabil = service.getPlanoContabil(formGroup) as any;

        expect(planoContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoContabil should not enable id FormControl', () => {
        const formGroup = service.createPlanoContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoContabil should disable id FormControl', () => {
        const formGroup = service.createPlanoContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
