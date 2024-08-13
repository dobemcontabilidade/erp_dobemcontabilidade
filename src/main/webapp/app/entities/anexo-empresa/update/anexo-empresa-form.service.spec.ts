import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-empresa.test-samples';

import { AnexoEmpresaFormService } from './anexo-empresa-form.service';

describe('AnexoEmpresa Form Service', () => {
  let service: AnexoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlAnexo: expect.any(Object),
            empresa: expect.any(Object),
            anexoRequeridoEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            urlAnexo: expect.any(Object),
            empresa: expect.any(Object),
            anexoRequeridoEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoEmpresa', () => {
      it('should return NewAnexoEmpresa for default AnexoEmpresa initial value', () => {
        const formGroup = service.createAnexoEmpresaFormGroup(sampleWithNewData);

        const anexoEmpresa = service.getAnexoEmpresa(formGroup) as any;

        expect(anexoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoEmpresa for empty AnexoEmpresa initial value', () => {
        const formGroup = service.createAnexoEmpresaFormGroup();

        const anexoEmpresa = service.getAnexoEmpresa(formGroup) as any;

        expect(anexoEmpresa).toMatchObject({});
      });

      it('should return IAnexoEmpresa', () => {
        const formGroup = service.createAnexoEmpresaFormGroup(sampleWithRequiredData);

        const anexoEmpresa = service.getAnexoEmpresa(formGroup) as any;

        expect(anexoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAnexoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoEmpresa should disable id FormControl', () => {
        const formGroup = service.createAnexoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
