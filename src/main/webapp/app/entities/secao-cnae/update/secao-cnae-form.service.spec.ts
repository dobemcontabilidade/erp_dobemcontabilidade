import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../secao-cnae.test-samples';

import { SecaoCnaeFormService } from './secao-cnae-form.service';

describe('SecaoCnae Form Service', () => {
  let service: SecaoCnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecaoCnaeFormService);
  });

  describe('Service methods', () => {
    describe('createSecaoCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSecaoCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing ISecaoCnae should create a new form with FormGroup', () => {
        const formGroup = service.createSecaoCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getSecaoCnae', () => {
      it('should return NewSecaoCnae for default SecaoCnae initial value', () => {
        const formGroup = service.createSecaoCnaeFormGroup(sampleWithNewData);

        const secaoCnae = service.getSecaoCnae(formGroup) as any;

        expect(secaoCnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewSecaoCnae for empty SecaoCnae initial value', () => {
        const formGroup = service.createSecaoCnaeFormGroup();

        const secaoCnae = service.getSecaoCnae(formGroup) as any;

        expect(secaoCnae).toMatchObject({});
      });

      it('should return ISecaoCnae', () => {
        const formGroup = service.createSecaoCnaeFormGroup(sampleWithRequiredData);

        const secaoCnae = service.getSecaoCnae(formGroup) as any;

        expect(secaoCnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISecaoCnae should not enable id FormControl', () => {
        const formGroup = service.createSecaoCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSecaoCnae should disable id FormControl', () => {
        const formGroup = service.createSecaoCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
