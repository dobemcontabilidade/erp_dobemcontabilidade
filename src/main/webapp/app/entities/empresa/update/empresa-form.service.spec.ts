import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../empresa.test-samples';

import { EmpresaFormService } from './empresa-form.service';

describe('Empresa Form Service', () => {
  let service: EmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            descricaoDoNegocio: expect.any(Object),
            dataAbertura: expect.any(Object),
            urlContratoSocial: expect.any(Object),
            capitalSocial: expect.any(Object),
            cnae: expect.any(Object),
            pessoaJuridica: expect.any(Object),
            empresa: expect.any(Object),
            ramo: expect.any(Object),
            enquadramento: expect.any(Object),
          }),
        );
      });

      it('passing IEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            descricaoDoNegocio: expect.any(Object),
            dataAbertura: expect.any(Object),
            urlContratoSocial: expect.any(Object),
            capitalSocial: expect.any(Object),
            cnae: expect.any(Object),
            pessoaJuridica: expect.any(Object),
            empresa: expect.any(Object),
            ramo: expect.any(Object),
            enquadramento: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmpresa', () => {
      it('should return NewEmpresa for default Empresa initial value', () => {
        const formGroup = service.createEmpresaFormGroup(sampleWithNewData);

        const empresa = service.getEmpresa(formGroup) as any;

        expect(empresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpresa for empty Empresa initial value', () => {
        const formGroup = service.createEmpresaFormGroup();

        const empresa = service.getEmpresa(formGroup) as any;

        expect(empresa).toMatchObject({});
      });

      it('should return IEmpresa', () => {
        const formGroup = service.createEmpresaFormGroup(sampleWithRequiredData);

        const empresa = service.getEmpresa(formGroup) as any;

        expect(empresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpresa should not enable id FormControl', () => {
        const formGroup = service.createEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmpresa should disable id FormControl', () => {
        const formGroup = service.createEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
