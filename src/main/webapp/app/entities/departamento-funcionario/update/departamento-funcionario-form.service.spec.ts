import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../departamento-funcionario.test-samples';

import { DepartamentoFuncionarioFormService } from './departamento-funcionario-form.service';

describe('DepartamentoFuncionario Form Service', () => {
  let service: DepartamentoFuncionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepartamentoFuncionarioFormService);
  });

  describe('Service methods', () => {
    describe('createDepartamentoFuncionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cargo: expect.any(Object),
            funcionario: expect.any(Object),
            departamento: expect.any(Object),
          }),
        );
      });

      it('passing IDepartamentoFuncionario should create a new form with FormGroup', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cargo: expect.any(Object),
            funcionario: expect.any(Object),
            departamento: expect.any(Object),
          }),
        );
      });
    });

    describe('getDepartamentoFuncionario', () => {
      it('should return NewDepartamentoFuncionario for default DepartamentoFuncionario initial value', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup(sampleWithNewData);

        const departamentoFuncionario = service.getDepartamentoFuncionario(formGroup) as any;

        expect(departamentoFuncionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewDepartamentoFuncionario for empty DepartamentoFuncionario initial value', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup();

        const departamentoFuncionario = service.getDepartamentoFuncionario(formGroup) as any;

        expect(departamentoFuncionario).toMatchObject({});
      });

      it('should return IDepartamentoFuncionario', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup(sampleWithRequiredData);

        const departamentoFuncionario = service.getDepartamentoFuncionario(formGroup) as any;

        expect(departamentoFuncionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDepartamentoFuncionario should not enable id FormControl', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDepartamentoFuncionario should disable id FormControl', () => {
        const formGroup = service.createDepartamentoFuncionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
