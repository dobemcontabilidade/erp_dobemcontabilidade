import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../opcao-razao-social-empresa.test-samples';

import { OpcaoRazaoSocialEmpresaFormService } from './opcao-razao-social-empresa-form.service';

describe('OpcaoRazaoSocialEmpresa Form Service', () => {
  let service: OpcaoRazaoSocialEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OpcaoRazaoSocialEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createOpcaoRazaoSocialEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            ordem: expect.any(Object),
            selecionado: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing IOpcaoRazaoSocialEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            ordem: expect.any(Object),
            selecionado: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getOpcaoRazaoSocialEmpresa', () => {
      it('should return NewOpcaoRazaoSocialEmpresa for default OpcaoRazaoSocialEmpresa initial value', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup(sampleWithNewData);

        const opcaoRazaoSocialEmpresa = service.getOpcaoRazaoSocialEmpresa(formGroup) as any;

        expect(opcaoRazaoSocialEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewOpcaoRazaoSocialEmpresa for empty OpcaoRazaoSocialEmpresa initial value', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup();

        const opcaoRazaoSocialEmpresa = service.getOpcaoRazaoSocialEmpresa(formGroup) as any;

        expect(opcaoRazaoSocialEmpresa).toMatchObject({});
      });

      it('should return IOpcaoRazaoSocialEmpresa', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup(sampleWithRequiredData);

        const opcaoRazaoSocialEmpresa = service.getOpcaoRazaoSocialEmpresa(formGroup) as any;

        expect(opcaoRazaoSocialEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOpcaoRazaoSocialEmpresa should not enable id FormControl', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOpcaoRazaoSocialEmpresa should disable id FormControl', () => {
        const formGroup = service.createOpcaoRazaoSocialEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
