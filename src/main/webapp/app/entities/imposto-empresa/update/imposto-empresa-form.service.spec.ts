import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../imposto-empresa.test-samples';

import { ImpostoEmpresaFormService } from './imposto-empresa-form.service';

describe('ImpostoEmpresa Form Service', () => {
  let service: ImpostoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpostoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createImpostoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImpostoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaVencimento: expect.any(Object),
            empresa: expect.any(Object),
            imposto: expect.any(Object),
          }),
        );
      });

      it('passing IImpostoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createImpostoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaVencimento: expect.any(Object),
            empresa: expect.any(Object),
            imposto: expect.any(Object),
          }),
        );
      });
    });

    describe('getImpostoEmpresa', () => {
      it('should return NewImpostoEmpresa for default ImpostoEmpresa initial value', () => {
        const formGroup = service.createImpostoEmpresaFormGroup(sampleWithNewData);

        const impostoEmpresa = service.getImpostoEmpresa(formGroup) as any;

        expect(impostoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewImpostoEmpresa for empty ImpostoEmpresa initial value', () => {
        const formGroup = service.createImpostoEmpresaFormGroup();

        const impostoEmpresa = service.getImpostoEmpresa(formGroup) as any;

        expect(impostoEmpresa).toMatchObject({});
      });

      it('should return IImpostoEmpresa', () => {
        const formGroup = service.createImpostoEmpresaFormGroup(sampleWithRequiredData);

        const impostoEmpresa = service.getImpostoEmpresa(formGroup) as any;

        expect(impostoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImpostoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createImpostoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImpostoEmpresa should disable id FormControl', () => {
        const formGroup = service.createImpostoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
