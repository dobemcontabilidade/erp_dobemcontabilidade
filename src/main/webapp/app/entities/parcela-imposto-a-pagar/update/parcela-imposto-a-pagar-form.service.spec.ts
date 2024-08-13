import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../parcela-imposto-a-pagar.test-samples';

import { ParcelaImpostoAPagarFormService } from './parcela-imposto-a-pagar-form.service';

describe('ParcelaImpostoAPagar Form Service', () => {
  let service: ParcelaImpostoAPagarFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParcelaImpostoAPagarFormService);
  });

  describe('Service methods', () => {
    describe('createParcelaImpostoAPagarFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroParcela: expect.any(Object),
            dataVencimento: expect.any(Object),
            dataPagamento: expect.any(Object),
            valor: expect.any(Object),
            valorMulta: expect.any(Object),
            urlArquivoPagamento: expect.any(Object),
            urlArquivoComprovante: expect.any(Object),
            mesCompetencia: expect.any(Object),
            parcelamentoImposto: expect.any(Object),
          }),
        );
      });

      it('passing IParcelaImpostoAPagar should create a new form with FormGroup', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroParcela: expect.any(Object),
            dataVencimento: expect.any(Object),
            dataPagamento: expect.any(Object),
            valor: expect.any(Object),
            valorMulta: expect.any(Object),
            urlArquivoPagamento: expect.any(Object),
            urlArquivoComprovante: expect.any(Object),
            mesCompetencia: expect.any(Object),
            parcelamentoImposto: expect.any(Object),
          }),
        );
      });
    });

    describe('getParcelaImpostoAPagar', () => {
      it('should return NewParcelaImpostoAPagar for default ParcelaImpostoAPagar initial value', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup(sampleWithNewData);

        const parcelaImpostoAPagar = service.getParcelaImpostoAPagar(formGroup) as any;

        expect(parcelaImpostoAPagar).toMatchObject(sampleWithNewData);
      });

      it('should return NewParcelaImpostoAPagar for empty ParcelaImpostoAPagar initial value', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup();

        const parcelaImpostoAPagar = service.getParcelaImpostoAPagar(formGroup) as any;

        expect(parcelaImpostoAPagar).toMatchObject({});
      });

      it('should return IParcelaImpostoAPagar', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup(sampleWithRequiredData);

        const parcelaImpostoAPagar = service.getParcelaImpostoAPagar(formGroup) as any;

        expect(parcelaImpostoAPagar).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IParcelaImpostoAPagar should not enable id FormControl', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewParcelaImpostoAPagar should disable id FormControl', () => {
        const formGroup = service.createParcelaImpostoAPagarFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
