import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cobranca-empresa.test-samples';

import { CobrancaEmpresaFormService } from './cobranca-empresa-form.service';

describe('CobrancaEmpresa Form Service', () => {
  let service: CobrancaEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CobrancaEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createCobrancaEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataCobranca: expect.any(Object),
            valorPago: expect.any(Object),
            urlCobranca: expect.any(Object),
            urlArquivo: expect.any(Object),
            valorCobrado: expect.any(Object),
            situacaoCobranca: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
            formaDePagamento: expect.any(Object),
          }),
        );
      });

      it('passing ICobrancaEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataCobranca: expect.any(Object),
            valorPago: expect.any(Object),
            urlCobranca: expect.any(Object),
            urlArquivo: expect.any(Object),
            valorCobrado: expect.any(Object),
            situacaoCobranca: expect.any(Object),
            assinaturaEmpresa: expect.any(Object),
            formaDePagamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getCobrancaEmpresa', () => {
      it('should return NewCobrancaEmpresa for default CobrancaEmpresa initial value', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup(sampleWithNewData);

        const cobrancaEmpresa = service.getCobrancaEmpresa(formGroup) as any;

        expect(cobrancaEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewCobrancaEmpresa for empty CobrancaEmpresa initial value', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup();

        const cobrancaEmpresa = service.getCobrancaEmpresa(formGroup) as any;

        expect(cobrancaEmpresa).toMatchObject({});
      });

      it('should return ICobrancaEmpresa', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup(sampleWithRequiredData);

        const cobrancaEmpresa = service.getCobrancaEmpresa(formGroup) as any;

        expect(cobrancaEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICobrancaEmpresa should not enable id FormControl', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCobrancaEmpresa should disable id FormControl', () => {
        const formGroup = service.createCobrancaEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
