import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../parcelamento-imposto.test-samples';

import { ParcelamentoImpostoFormService } from './parcelamento-imposto-form.service';

describe('ParcelamentoImposto Form Service', () => {
  let service: ParcelamentoImpostoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParcelamentoImpostoFormService);
  });

  describe('Service methods', () => {
    describe('createParcelamentoImpostoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaVencimento: expect.any(Object),
            numeroParcelas: expect.any(Object),
            urlArquivoNegociacao: expect.any(Object),
            numeroParcelasPagas: expect.any(Object),
            numeroParcelasRegatantes: expect.any(Object),
            situacaoSolicitacaoParcelamentoEnum: expect.any(Object),
            imposto: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });

      it('passing IParcelamentoImposto should create a new form with FormGroup', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diaVencimento: expect.any(Object),
            numeroParcelas: expect.any(Object),
            urlArquivoNegociacao: expect.any(Object),
            numeroParcelasPagas: expect.any(Object),
            numeroParcelasRegatantes: expect.any(Object),
            situacaoSolicitacaoParcelamentoEnum: expect.any(Object),
            imposto: expect.any(Object),
            empresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getParcelamentoImposto', () => {
      it('should return NewParcelamentoImposto for default ParcelamentoImposto initial value', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup(sampleWithNewData);

        const parcelamentoImposto = service.getParcelamentoImposto(formGroup) as any;

        expect(parcelamentoImposto).toMatchObject(sampleWithNewData);
      });

      it('should return NewParcelamentoImposto for empty ParcelamentoImposto initial value', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup();

        const parcelamentoImposto = service.getParcelamentoImposto(formGroup) as any;

        expect(parcelamentoImposto).toMatchObject({});
      });

      it('should return IParcelamentoImposto', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup(sampleWithRequiredData);

        const parcelamentoImposto = service.getParcelamentoImposto(formGroup) as any;

        expect(parcelamentoImposto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParcelamentoImposto should not enable id FormControl', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParcelamentoImposto should disable id FormControl', () => {
        const formGroup = service.createParcelamentoImpostoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
