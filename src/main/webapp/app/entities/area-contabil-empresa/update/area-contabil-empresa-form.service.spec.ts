import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../area-contabil-empresa.test-samples';

import { AreaContabilEmpresaFormService } from './area-contabil-empresa-form.service';

describe('AreaContabilEmpresa Form Service', () => {
  let service: AreaContabilEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaContabilEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAreaContabilEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pontuacao: expect.any(Object),
            depoimento: expect.any(Object),
            reclamacao: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IAreaContabilEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            pontuacao: expect.any(Object),
            depoimento: expect.any(Object),
            reclamacao: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getAreaContabilEmpresa', () => {
      it('should return NewAreaContabilEmpresa for default AreaContabilEmpresa initial value', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup(sampleWithNewData);

        const areaContabilEmpresa = service.getAreaContabilEmpresa(formGroup) as any;

        expect(areaContabilEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAreaContabilEmpresa for empty AreaContabilEmpresa initial value', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup();

        const areaContabilEmpresa = service.getAreaContabilEmpresa(formGroup) as any;

        expect(areaContabilEmpresa).toMatchObject({});
      });

      it('should return IAreaContabilEmpresa', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup(sampleWithRequiredData);

        const areaContabilEmpresa = service.getAreaContabilEmpresa(formGroup) as any;

        expect(areaContabilEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAreaContabilEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAreaContabilEmpresa should disable id FormControl', () => {
        const formGroup = service.createAreaContabilEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
