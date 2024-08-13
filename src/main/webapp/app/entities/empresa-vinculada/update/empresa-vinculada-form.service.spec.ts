import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../empresa-vinculada.test-samples';

import { EmpresaVinculadaFormService } from './empresa-vinculada-form.service';

describe('EmpresaVinculada Form Service', () => {
  let service: EmpresaVinculadaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpresaVinculadaFormService);
  });

  describe('Service methods', () => {
    describe('createEmpresaVinculadaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeEmpresa: expect.any(Object),
            cnpj: expect.any(Object),
            remuneracaoEmpresa: expect.any(Object),
            observacoes: expect.any(Object),
            salarioFixo: expect.any(Object),
            salarioVariavel: expect.any(Object),
            valorSalarioFixo: expect.any(Object),
            dataTerminoContrato: expect.any(Object),
            numeroInscricao: expect.any(Object),
            codigoLotacao: expect.any(Object),
            descricaoComplementar: expect.any(Object),
            descricaoCargo: expect.any(Object),
            observacaoJornadaTrabalho: expect.any(Object),
            mediaHorasTrabalhadasSemana: expect.any(Object),
            regimePrevidenciario: expect.any(Object),
            unidadePagamentoSalario: expect.any(Object),
            jornadaEspecial: expect.any(Object),
            tipoInscricaoEmpresaVinculada: expect.any(Object),
            tipoContratoTrabalho: expect.any(Object),
            tipoRegimeTrabalho: expect.any(Object),
            diasDaSemana: expect.any(Object),
            tipoJornadaEmpresaVinculada: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IEmpresaVinculada should create a new form with FormGroup', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeEmpresa: expect.any(Object),
            cnpj: expect.any(Object),
            remuneracaoEmpresa: expect.any(Object),
            observacoes: expect.any(Object),
            salarioFixo: expect.any(Object),
            salarioVariavel: expect.any(Object),
            valorSalarioFixo: expect.any(Object),
            dataTerminoContrato: expect.any(Object),
            numeroInscricao: expect.any(Object),
            codigoLotacao: expect.any(Object),
            descricaoComplementar: expect.any(Object),
            descricaoCargo: expect.any(Object),
            observacaoJornadaTrabalho: expect.any(Object),
            mediaHorasTrabalhadasSemana: expect.any(Object),
            regimePrevidenciario: expect.any(Object),
            unidadePagamentoSalario: expect.any(Object),
            jornadaEspecial: expect.any(Object),
            tipoInscricaoEmpresaVinculada: expect.any(Object),
            tipoContratoTrabalho: expect.any(Object),
            tipoRegimeTrabalho: expect.any(Object),
            diasDaSemana: expect.any(Object),
            tipoJornadaEmpresaVinculada: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getEmpresaVinculada', () => {
      it('should return NewEmpresaVinculada for default EmpresaVinculada initial value', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup(sampleWithNewData);

        const empresaVinculada = service.getEmpresaVinculada(formGroup) as any;

        expect(empresaVinculada).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmpresaVinculada for empty EmpresaVinculada initial value', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup();

        const empresaVinculada = service.getEmpresaVinculada(formGroup) as any;

        expect(empresaVinculada).toMatchObject({});
      });

      it('should return IEmpresaVinculada', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup(sampleWithRequiredData);

        const empresaVinculada = service.getEmpresaVinculada(formGroup) as any;

        expect(empresaVinculada).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmpresaVinculada should not enable id FormControl', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmpresaVinculada should disable id FormControl', () => {
        const formGroup = service.createEmpresaVinculadaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
