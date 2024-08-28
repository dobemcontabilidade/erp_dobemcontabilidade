import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fornecedor-certificado.test-samples';

import { FornecedorCertificadoFormService } from './fornecedor-certificado-form.service';

describe('FornecedorCertificado Form Service', () => {
  let service: FornecedorCertificadoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FornecedorCertificadoFormService);
  });

  describe('Service methods', () => {
    describe('createFornecedorCertificadoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            sigla: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IFornecedorCertificado should create a new form with FormGroup', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razaoSocial: expect.any(Object),
            sigla: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getFornecedorCertificado', () => {
      it('should return NewFornecedorCertificado for default FornecedorCertificado initial value', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup(sampleWithNewData);

        const fornecedorCertificado = service.getFornecedorCertificado(formGroup) as any;

        expect(fornecedorCertificado).toMatchObject(sampleWithNewData);
      });

      it('should return NewFornecedorCertificado for empty FornecedorCertificado initial value', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup();

        const fornecedorCertificado = service.getFornecedorCertificado(formGroup) as any;

        expect(fornecedorCertificado).toMatchObject({});
      });

      it('should return IFornecedorCertificado', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup(sampleWithRequiredData);

        const fornecedorCertificado = service.getFornecedorCertificado(formGroup) as any;

        expect(fornecedorCertificado).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFornecedorCertificado should not enable id FormControl', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFornecedorCertificado should disable id FormControl', () => {
        const formGroup = service.createFornecedorCertificadoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
