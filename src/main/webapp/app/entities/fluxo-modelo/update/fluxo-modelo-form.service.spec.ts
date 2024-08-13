import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../fluxo-modelo.test-samples';

import { FluxoModeloFormService } from './fluxo-modelo-form.service';

describe('FluxoModelo Form Service', () => {
  let service: FluxoModeloFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FluxoModeloFormService);
  });

  describe('Service methods', () => {
    describe('createFluxoModeloFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFluxoModeloFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });

      it('passing IFluxoModelo should create a new form with FormGroup', () => {
        const formGroup = service.createFluxoModeloFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            cidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getFluxoModelo', () => {
      it('should return NewFluxoModelo for default FluxoModelo initial value', () => {
        const formGroup = service.createFluxoModeloFormGroup(sampleWithNewData);

        const fluxoModelo = service.getFluxoModelo(formGroup) as any;

        expect(fluxoModelo).toMatchObject(sampleWithNewData);
      });

      it('should return NewFluxoModelo for empty FluxoModelo initial value', () => {
        const formGroup = service.createFluxoModeloFormGroup();

        const fluxoModelo = service.getFluxoModelo(formGroup) as any;

        expect(fluxoModelo).toMatchObject({});
      });

      it('should return IFluxoModelo', () => {
        const formGroup = service.createFluxoModeloFormGroup(sampleWithRequiredData);

        const fluxoModelo = service.getFluxoModelo(formGroup) as any;

        expect(fluxoModelo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFluxoModelo should not enable id FormControl', () => {
        const formGroup = service.createFluxoModeloFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFluxoModelo should disable id FormControl', () => {
        const formGroup = service.createFluxoModeloFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
