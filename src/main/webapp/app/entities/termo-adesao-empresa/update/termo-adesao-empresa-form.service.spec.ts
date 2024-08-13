import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../termo-adesao-empresa.test-samples';

import { TermoAdesaoEmpresaFormService } from './termo-adesao-empresa-form.service';

describe('TermoAdesaoEmpresa Form Service', () => {
  let service: TermoAdesaoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermoAdesaoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createTermoAdesaoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdesao: expect.any(Object),
            checked: expect.any(Object),
            urlDoc: expect.any(Object),
            empresa: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });

      it('passing ITermoAdesaoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdesao: expect.any(Object),
            checked: expect.any(Object),
            urlDoc: expect.any(Object),
            empresa: expect.any(Object),
            planoContabil: expect.any(Object),
          }),
        );
      });
    });

    describe('getTermoAdesaoEmpresa', () => {
      it('should return NewTermoAdesaoEmpresa for default TermoAdesaoEmpresa initial value', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup(sampleWithNewData);

        const termoAdesaoEmpresa = service.getTermoAdesaoEmpresa(formGroup) as any;

        expect(termoAdesaoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewTermoAdesaoEmpresa for empty TermoAdesaoEmpresa initial value', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup();

        const termoAdesaoEmpresa = service.getTermoAdesaoEmpresa(formGroup) as any;

        expect(termoAdesaoEmpresa).toMatchObject({});
      });

      it('should return ITermoAdesaoEmpresa', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup(sampleWithRequiredData);

        const termoAdesaoEmpresa = service.getTermoAdesaoEmpresa(formGroup) as any;

        expect(termoAdesaoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITermoAdesaoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTermoAdesaoEmpresa should disable id FormControl', () => {
        const formGroup = service.createTermoAdesaoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
