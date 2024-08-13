import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../imposto-parcelado.test-samples';

import { ImpostoParceladoFormService } from './imposto-parcelado-form.service';

describe('ImpostoParcelado Form Service', () => {
  let service: ImpostoParceladoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpostoParceladoFormService);
  });

  describe('Service methods', () => {
    describe('createImpostoParceladoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImpostoParceladoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diasAtraso: expect.any(Object),
            parcelamentoImposto: expect.any(Object),
            impostoAPagarEmpresa: expect.any(Object),
          }),
        );
      });

      it('passing IImpostoParcelado should create a new form with FormGroup', () => {
        const formGroup = service.createImpostoParceladoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            diasAtraso: expect.any(Object),
            parcelamentoImposto: expect.any(Object),
            impostoAPagarEmpresa: expect.any(Object),
          }),
        );
      });
    });

    describe('getImpostoParcelado', () => {
      it('should return NewImpostoParcelado for default ImpostoParcelado initial value', () => {
        const formGroup = service.createImpostoParceladoFormGroup(sampleWithNewData);

        const impostoParcelado = service.getImpostoParcelado(formGroup) as any;

        expect(impostoParcelado).toMatchObject(sampleWithNewData);
      });

      it('should return NewImpostoParcelado for empty ImpostoParcelado initial value', () => {
        const formGroup = service.createImpostoParceladoFormGroup();

        const impostoParcelado = service.getImpostoParcelado(formGroup) as any;

        expect(impostoParcelado).toMatchObject({});
      });

      it('should return IImpostoParcelado', () => {
        const formGroup = service.createImpostoParceladoFormGroup(sampleWithRequiredData);

        const impostoParcelado = service.getImpostoParcelado(formGroup) as any;

        expect(impostoParcelado).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImpostoParcelado should not enable id FormControl', () => {
        const formGroup = service.createImpostoParceladoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImpostoParcelado should disable id FormControl', () => {
        const formGroup = service.createImpostoParceladoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
