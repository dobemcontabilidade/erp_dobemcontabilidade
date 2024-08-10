import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../plano-conta-azul.test-samples';

import { PlanoContaAzulFormService } from './plano-conta-azul-form.service';

describe('PlanoContaAzul Form Service', () => {
  let service: PlanoContaAzulFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanoContaAzulFormService);
  });

  describe('Service methods', () => {
    describe('createPlanoContaAzulFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlanoContaAzulFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            valorBase: expect.any(Object),
            usuarios: expect.any(Object),
            boletos: expect.any(Object),
            notaFiscalProduto: expect.any(Object),
            notaFiscalServico: expect.any(Object),
            notaFiscalCe: expect.any(Object),
            suporte: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });

      it('passing IPlanoContaAzul should create a new form with FormGroup', () => {
        const formGroup = service.createPlanoContaAzulFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            valorBase: expect.any(Object),
            usuarios: expect.any(Object),
            boletos: expect.any(Object),
            notaFiscalProduto: expect.any(Object),
            notaFiscalServico: expect.any(Object),
            notaFiscalCe: expect.any(Object),
            suporte: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlanoContaAzul', () => {
      it('should return NewPlanoContaAzul for default PlanoContaAzul initial value', () => {
        const formGroup = service.createPlanoContaAzulFormGroup(sampleWithNewData);

        const planoContaAzul = service.getPlanoContaAzul(formGroup) as any;

        expect(planoContaAzul).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlanoContaAzul for empty PlanoContaAzul initial value', () => {
        const formGroup = service.createPlanoContaAzulFormGroup();

        const planoContaAzul = service.getPlanoContaAzul(formGroup) as any;

        expect(planoContaAzul).toMatchObject({});
      });

      it('should return IPlanoContaAzul', () => {
        const formGroup = service.createPlanoContaAzulFormGroup(sampleWithRequiredData);

        const planoContaAzul = service.getPlanoContaAzul(formGroup) as any;

        expect(planoContaAzul).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlanoContaAzul should not enable id FormControl', () => {
        const formGroup = service.createPlanoContaAzulFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlanoContaAzul should disable id FormControl', () => {
        const formGroup = service.createPlanoContaAzulFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
