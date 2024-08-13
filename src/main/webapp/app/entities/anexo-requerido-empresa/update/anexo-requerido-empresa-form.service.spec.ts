import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-requerido-empresa.test-samples';

import { AnexoRequeridoEmpresaFormService } from './anexo-requerido-empresa-form.service';

describe('AnexoRequeridoEmpresa Form Service', () => {
  let service: AnexoRequeridoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoRequeridoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoRequeridoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            urlArquivo: expect.any(Object),
            anexoRequerido: expect.any(Object),
            enquadramento: expect.any(Object),
            tributacao: expect.any(Object),
            ramo: expect.any(Object),
            empresa: expect.any(Object),
            empresaModelo: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoRequeridoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            obrigatorio: expect.any(Object),
            urlArquivo: expect.any(Object),
            anexoRequerido: expect.any(Object),
            enquadramento: expect.any(Object),
            tributacao: expect.any(Object),
            ramo: expect.any(Object),
            empresa: expect.any(Object),
            empresaModelo: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoRequeridoEmpresa', () => {
      it('should return NewAnexoRequeridoEmpresa for default AnexoRequeridoEmpresa initial value', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup(sampleWithNewData);

        const anexoRequeridoEmpresa = service.getAnexoRequeridoEmpresa(formGroup) as any;

        expect(anexoRequeridoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoRequeridoEmpresa for empty AnexoRequeridoEmpresa initial value', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup();

        const anexoRequeridoEmpresa = service.getAnexoRequeridoEmpresa(formGroup) as any;

        expect(anexoRequeridoEmpresa).toMatchObject({});
      });

      it('should return IAnexoRequeridoEmpresa', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup(sampleWithRequiredData);

        const anexoRequeridoEmpresa = service.getAnexoRequeridoEmpresa(formGroup) as any;

        expect(anexoRequeridoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoRequeridoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoRequeridoEmpresa should disable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
