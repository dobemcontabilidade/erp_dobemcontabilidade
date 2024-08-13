import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../imposto-empresa-modelo.test-samples';

import { ImpostoEmpresaModeloFormService } from './imposto-empresa-modelo-form.service';

describe('ImpostoEmpresaModelo Form Service', () => {
  let service: ImpostoEmpresaModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ImpostoEmpresaModeloFormService);
  });

  describe('Service methods', () => {
    describe('createImpostoEmpresaModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            observacao: expect.any(Object),
            empresaModelo: expect.any(Object),
            imposto: expect.any(Object),
          }),
        );
      });

      it('passing IImpostoEmpresaModelo should create a new form with FormGroup', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            observacao: expect.any(Object),
            empresaModelo: expect.any(Object),
            imposto: expect.any(Object),
          }),
        );
      });
    });

    describe('getImpostoEmpresaModelo', () => {
      it('should return NewImpostoEmpresaModelo for default ImpostoEmpresaModelo initial value', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup(sampleWithNewData);

        const impostoEmpresaModelo = service.getImpostoEmpresaModelo(formGroup) as any;

        expect(impostoEmpresaModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewImpostoEmpresaModelo for empty ImpostoEmpresaModelo initial value', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup();

        const impostoEmpresaModelo = service.getImpostoEmpresaModelo(formGroup) as any;

        expect(impostoEmpresaModelo).toMatchObject({});
      });

      it('should return IImpostoEmpresaModelo', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup(sampleWithRequiredData);

        const impostoEmpresaModelo = service.getImpostoEmpresaModelo(formGroup) as any;

        expect(impostoEmpresaModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IImpostoEmpresaModelo should not enable id FormControl', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewImpostoEmpresaModelo should disable id FormControl', () => {
        const formGroup = service.createImpostoEmpresaModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
