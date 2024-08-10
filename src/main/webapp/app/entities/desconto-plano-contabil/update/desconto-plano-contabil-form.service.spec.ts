import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../desconto-plano-contabil.test-samples';

import { DescontoPlanoContabilFormService } from './desconto-plano-contabil-form.service';

describe('DescontoPlanoContabil Form Service', () => {
  let service: DescontoPlanoContabilFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DescontoPlanoContabilFormService);
  });

  describe('Service methods', () => {
    describe('createDescontoPlanoContabilFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentual: expect.any(Object),
            periodoPagamento: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });

      it('passing IDescontoPlanoContabil should create a new form with FormGroup', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            percentual: expect.any(Object),
            periodoPagamento: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getDescontoPlanoContabil', () => {
      it('should return NewDescontoPlanoContabil for default DescontoPlanoContabil initial value', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup(sampleWithNewData);

        const descontoPlanoContabil = service.getDescontoPlanoContabil(formGroup) as any;

        expect(descontoPlanoContabil).toMatchObject(sampleWithNewData);
      });

      it('should return NewDescontoPlanoContabil for empty DescontoPlanoContabil initial value', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup();

        const descontoPlanoContabil = service.getDescontoPlanoContabil(formGroup) as any;

        expect(descontoPlanoContabil).toMatchObject({});
      });

      it('should return IDescontoPlanoContabil', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup(sampleWithRequiredData);

        const descontoPlanoContabil = service.getDescontoPlanoContabil(formGroup) as any;

        expect(descontoPlanoContabil).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDescontoPlanoContabil should not enable id FormControl', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDescontoPlanoContabil should disable id FormControl', () => {
        const formGroup = service.createDescontoPlanoContabilFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
