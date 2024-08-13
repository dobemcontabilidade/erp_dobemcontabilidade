import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../servico-contabil-assinatura-empresa.test-samples';

import { ServicoContabilAssinaturaEmpresaFormService } from './servico-contabil-assinatura-empresa-form.service';

describe('ServicoContabilAssinaturaEmpresa Form Service', () => {
  let service: ServicoContabilAssinaturaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicoContabilAssinaturaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createServicoContabilAssinaturaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataLegal: expect.any(Object),
            dataAdmin: expect.any(Object),
            servicoContabil: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IServicoContabilAssinaturaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataLegal: expect.any(Object),
            dataAdmin: expect.any(Object),
            servicoContabil: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getServicoContabilAssinaturaEmpresa', () => {
      it('should return NewServicoContabilAssinaturaEmpresa for default ServicoContabilAssinaturaEmpresa initial value', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup(sampleWithNewData);

        const servicoContabilAssinaturaEmpresa = service.getServicoContabilAssinaturaEmpresa(formGroup) as any;

        expect(servicoContabilAssinaturaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewServicoContabilAssinaturaEmpresa for empty ServicoContabilAssinaturaEmpresa initial value', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup();

        const servicoContabilAssinaturaEmpresa = service.getServicoContabilAssinaturaEmpresa(formGroup) as any;

        expect(servicoContabilAssinaturaEmpresa).toMatchObject({});
      });

      it('should return IServicoContabilAssinaturaEmpresa', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup(sampleWithRequiredData);

        const servicoContabilAssinaturaEmpresa = service.getServicoContabilAssinaturaEmpresa(formGroup) as any;

        expect(servicoContabilAssinaturaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IServicoContabilAssinaturaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewServicoContabilAssinaturaEmpresa should disable id FormControl', () => {
        const formGroup = service.createServicoContabilAssinaturaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
