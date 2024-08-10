import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../adicional-tributacao.test-samples';

import { AdicionalTributacaoFormService } from './adicional-tributacao-form.service';

describe('AdicionalTributacao Form Service', () => {
  let service: AdicionalTributacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdicionalTributacaoFormService);
  });

  describe('Service methods', () => {
    describe('createAdicionalTributacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            tributacao: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });

      it('passing IAdicionalTributacao should create a new form with FormGroup', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            tributacao: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getAdicionalTributacao', () => {
      it('should return NewAdicionalTributacao for default AdicionalTributacao initial value', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup(sampleWithNewData);

        const adicionalTributacao = service.getAdicionalTributacao(formGroup) as any;

        expect(adicionalTributacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewAdicionalTributacao for empty AdicionalTributacao initial value', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup();

        const adicionalTributacao = service.getAdicionalTributacao(formGroup) as any;

        expect(adicionalTributacao).toMatchObject({});
      });

      it('should return IAdicionalTributacao', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup(sampleWithRequiredData);

        const adicionalTributacao = service.getAdicionalTributacao(formGroup) as any;

        expect(adicionalTributacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAdicionalTributacao should not enable id FormControl', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAdicionalTributacao should disable id FormControl', () => {
        const formGroup = service.createAdicionalTributacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
