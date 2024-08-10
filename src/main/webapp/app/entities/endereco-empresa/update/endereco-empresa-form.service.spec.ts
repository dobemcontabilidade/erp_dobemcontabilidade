import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../endereco-empresa.test-samples';

import { EnderecoEmpresaFormService } from './endereco-empresa-form.service';

describe('EnderecoEmpresa Form Service', () => {
  let service: EnderecoEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnderecoEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createEnderecoEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            bairro: expect.any(Object),
            cep: expect.any(Object),
            principal: expect.any(Object),
            filial: expect.any(Object),
            enderecoFiscal: expect.any(Object),
            empresa: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });

      it('passing IEnderecoEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            logradouro: expect.any(Object),
            numero: expect.any(Object),
            complemento: expect.any(Object),
            bairro: expect.any(Object),
            cep: expect.any(Object),
            principal: expect.any(Object),
            filial: expect.any(Object),
            enderecoFiscal: expect.any(Object),
            empresa: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getEnderecoEmpresa', () => {
      it('should return NewEnderecoEmpresa for default EnderecoEmpresa initial value', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup(sampleWithNewData);

        const enderecoEmpresa = service.getEnderecoEmpresa(formGroup) as any;

        expect(enderecoEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnderecoEmpresa for empty EnderecoEmpresa initial value', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup();

        const enderecoEmpresa = service.getEnderecoEmpresa(formGroup) as any;

        expect(enderecoEmpresa).toMatchObject({});
      });

      it('should return IEnderecoEmpresa', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup(sampleWithRequiredData);

        const enderecoEmpresa = service.getEnderecoEmpresa(formGroup) as any;

        expect(enderecoEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnderecoEmpresa should not enable id FormControl', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnderecoEmpresa should disable id FormControl', () => {
        const formGroup = service.createEnderecoEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
