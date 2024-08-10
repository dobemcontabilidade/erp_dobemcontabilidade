import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../grupo-cnae.test-samples';

import { GrupoCnaeFormService } from './grupo-cnae-form.service';

describe('GrupoCnae Form Service', () => {
  let service: GrupoCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrupoCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createGrupoCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createGrupoCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            divisao: expect.any(Object),
          }),
        );
      });

      it('passing IGrupoCnae should create a new form with FormGroup', () => {
        const formGroup = service.createGrupoCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            divisao: expect.any(Object),
          }),
        );
      });
    });

    describe('getGrupoCnae', () => {
      it('should return NewGrupoCnae for default GrupoCnae initial value', () => {
        const formGroup = service.createGrupoCnaeFormGroup(sampleWithNewData);

        const grupoCnae = service.getGrupoCnae(formGroup) as any;

        expect(grupoCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewGrupoCnae for empty GrupoCnae initial value', () => {
        const formGroup = service.createGrupoCnaeFormGroup();

        const grupoCnae = service.getGrupoCnae(formGroup) as any;

        expect(grupoCnae).toMatchObject({});
      });

      it('should return IGrupoCnae', () => {
        const formGroup = service.createGrupoCnaeFormGroup(sampleWithRequiredData);

        const grupoCnae = service.getGrupoCnae(formGroup) as any;

        expect(grupoCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IGrupoCnae should not enable id FormControl', () => {
        const formGroup = service.createGrupoCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewGrupoCnae should disable id FormControl', () => {
        const formGroup = service.createGrupoCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
