import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../divisao-cnae.test-samples';

import { DivisaoCnaeFormService } from './divisao-cnae-form.service';

describe('DivisaoCnae Form Service', () => {
  let service: DivisaoCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DivisaoCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createDivisaoCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDivisaoCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            secao: expect.any(Object),
          }),
        );
      });

      it('passing IDivisaoCnae should create a new form with FormGroup', () => {
        const formGroup = service.createDivisaoCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            secao: expect.any(Object),
          }),
        );
      });
    });

    describe('getDivisaoCnae', () => {
      it('should return NewDivisaoCnae for default DivisaoCnae initial value', () => {
        const formGroup = service.createDivisaoCnaeFormGroup(sampleWithNewData);

        const divisaoCnae = service.getDivisaoCnae(formGroup) as any;

        expect(divisaoCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewDivisaoCnae for empty DivisaoCnae initial value', () => {
        const formGroup = service.createDivisaoCnaeFormGroup();

        const divisaoCnae = service.getDivisaoCnae(formGroup) as any;

        expect(divisaoCnae).toMatchObject({});
      });

      it('should return IDivisaoCnae', () => {
        const formGroup = service.createDivisaoCnaeFormGroup(sampleWithRequiredData);

        const divisaoCnae = service.getDivisaoCnae(formGroup) as any;

        expect(divisaoCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDivisaoCnae should not enable id FormControl', () => {
        const formGroup = service.createDivisaoCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDivisaoCnae should disable id FormControl', () => {
        const formGroup = service.createDivisaoCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
