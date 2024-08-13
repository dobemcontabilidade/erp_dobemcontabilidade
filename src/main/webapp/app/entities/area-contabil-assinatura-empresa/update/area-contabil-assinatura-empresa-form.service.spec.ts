import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../area-contabil-assinatura-empresa.test-samples';

import { AreaContabilAssinaturaEmpresaFormService } from './area-contabil-assinatura-empresa-form.service';

describe('AreaContabilAssinaturaEmpresa Form Service', () => {
  let service: AreaContabilAssinaturaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AreaContabilAssinaturaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAreaContabilAssinaturaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            dataRevogacao: expect.any(Object),
            areaContabil: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });

      it('passing IAreaContabilAssinaturaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            ativo: expect.any(Object),
            dataAtribuicao: expect.any(Object),
            dataRevogacao: expect.any(Object),
            areaContabil: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
            contador: expect.any(Object),
          }),
        );
      });
    });

    describe('getAreaContabilAssinaturaEmpresa', () => {
      it('should return NewAreaContabilAssinaturaEmpresa for default AreaContabilAssinaturaEmpresa initial value', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup(sampleWithNewData);

        const areaContabilAssinaturaEmpresa = service.getAreaContabilAssinaturaEmpresa(formGroup) as any;

        expect(areaContabilAssinaturaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAreaContabilAssinaturaEmpresa for empty AreaContabilAssinaturaEmpresa initial value', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup();

        const areaContabilAssinaturaEmpresa = service.getAreaContabilAssinaturaEmpresa(formGroup) as any;

        expect(areaContabilAssinaturaEmpresa).toMatchObject({});
      });

      it('should return IAreaContabilAssinaturaEmpresa', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        const areaContabilAssinaturaEmpresa = service.getAreaContabilAssinaturaEmpresa(formGroup) as any;

        expect(areaContabilAssinaturaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAreaContabilAssinaturaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAreaContabilAssinaturaEmpresa should disable id FormControl', () => {
        const formGroup = service.createAreaContabilAssinaturaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
