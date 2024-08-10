import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cnae.test-samples';

import { CnaeFormService } from './cnae-form.service';

describe('Cnae Form Service', () => {
  let service: CnaeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CnaeFormService);
  });

  describe('Service methods', () => {
    describe('createCnaeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCnaeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            anexo: expect.any(Object),
            atendidoFreemium: expect.any(Object),
            atendido: expect.any(Object),
            optanteSimples: expect.any(Object),
            categoria: expect.any(Object),
          }),
        );
      });

      it('passing ICnae should create a new form with FormGroup', () => {
        const formGroup = service.createCnaeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            codigo: expect.any(Object),
            descricao: expect.any(Object),
            anexo: expect.any(Object),
            atendidoFreemium: expect.any(Object),
            atendido: expect.any(Object),
            optanteSimples: expect.any(Object),
            categoria: expect.any(Object),
          }),
        );
      });
    });

    describe('getCnae', () => {
      it('should return NewCnae for default Cnae initial value', () => {
        const formGroup = service.createCnaeFormGroup(sampleWithNewData);

        const cnae = service.getCnae(formGroup) as any;

        expect(cnae).toMatchObject(sampleWithNewData);
      });

      it('should return NewCnae for empty Cnae initial value', () => {
        const formGroup = service.createCnaeFormGroup();

        const cnae = service.getCnae(formGroup) as any;

        expect(cnae).toMatchObject({});
      });

      it('should return ICnae', () => {
        const formGroup = service.createCnaeFormGroup(sampleWithRequiredData);

        const cnae = service.getCnae(formGroup) as any;

        expect(cnae).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICnae should not enable id FormControl', () => {
        const formGroup = service.createCnaeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCnae should disable id FormControl', () => {
        const formGroup = service.createCnaeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
