import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../imposto-a-pagar-empresa.test-samples';

import { ImpostoAPagarEmpresaFormService } from './imposto-a-pagar-empresa-form.service';

describe('ImpostoAPagarEmpresa Form Service', () => {
  let service: ImpostoAPagarEmpresaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpostoAPagarEmpresaFormService);
  });

  describe('Service methods', () => {
    describe('createImpostoAPagarEmpresaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataVencimento: expect.any(Object),
            dataPagamento: expect.any(Object),
            valor: expect.any(Object),
            valorMulta: expect.any(Object),
            urlArquivoPagamento: expect.any(Object),
            urlArquivoComprovante: expect.any(Object),
            situacaoPagamentoImpostoEnum: expect.any(Object),
            imposto: expect.any(Object),
          }),
        );
      });

      it('passing IImpostoAPagarEmpresa should create a new form with FormGroup', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataVencimento: expect.any(Object),
            dataPagamento: expect.any(Object),
            valor: expect.any(Object),
            valorMulta: expect.any(Object),
            urlArquivoPagamento: expect.any(Object),
            urlArquivoComprovante: expect.any(Object),
            situacaoPagamentoImpostoEnum: expect.any(Object),
            imposto: expect.any(Object),
          }),
        );
      });
    });

    describe('getImpostoAPagarEmpresa', () => {
      it('should return NewImpostoAPagarEmpresa for default ImpostoAPagarEmpresa initial value', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup(sampleWithNewData);

        const impostoAPagarEmpresa = service.getImpostoAPagarEmpresa(formGroup) as any;

        expect(impostoAPagarEmpresa).toMatchObject(sampleWithNewData);
      });

      it('should return NewImpostoAPagarEmpresa for empty ImpostoAPagarEmpresa initial value', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup();

        const impostoAPagarEmpresa = service.getImpostoAPagarEmpresa(formGroup) as any;

        expect(impostoAPagarEmpresa).toMatchObject({});
      });

      it('should return IImpostoAPagarEmpresa', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup(sampleWithRequiredData);

        const impostoAPagarEmpresa = service.getImpostoAPagarEmpresa(formGroup) as any;

        expect(impostoAPagarEmpresa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImpostoAPagarEmpresa should not enable id FormControl', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImpostoAPagarEmpresa should disable id FormControl', () => {
        const formGroup = service.createImpostoAPagarEmpresaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
