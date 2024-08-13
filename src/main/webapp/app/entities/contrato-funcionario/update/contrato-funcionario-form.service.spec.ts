import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../contrato-funcionario.test-samples';

import { ContratoFuncionarioFormService } from './contrato-funcionario-form.service';

describe('ContratoFuncionario Form Service', () => {
  let service: ContratoFuncionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContratoFuncionarioFormService);
  });

  describe('Service methods', () => {
    describe('createContratoFuncionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContratoFuncionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            salarioFixo: expect.any(Object),
            salarioVariavel: expect.any(Object),
            estagio: expect.any(Object),
            naturezaEstagioEnum: expect.any(Object),
            ctps: expect.any(Object),
            serieCtps: expect.any(Object),
            orgaoEmissorDocumento: expect.any(Object),
            dataValidadeDocumento: expect.any(Object),
            dataAdmissao: expect.any(Object),
            cargo: expect.any(Object),
            descricaoAtividades: expect.any(Object),
            situacao: expect.any(Object),
            valorSalarioFixo: expect.any(Object),
            valorSalarioVariavel: expect.any(Object),
            dataTerminoContrato: expect.any(Object),
            datainicioContrato: expect.any(Object),
            horasATrabalhadar: expect.any(Object),
            codigoCargo: expect.any(Object),
            categoriaTrabalhador: expect.any(Object),
            tipoVinculoTrabalho: expect.any(Object),
            fgtsOpcao: expect.any(Object),
            tIpoDocumentoEnum: expect.any(Object),
            periodoExperiencia: expect.any(Object),
            tipoAdmisaoEnum: expect.any(Object),
            periodoIntermitente: expect.any(Object),
            indicativoAdmissao: expect.any(Object),
            numeroPisNisPasep: expect.any(Object),
            funcionario: expect.any(Object),
            agenteIntegracaoEstagio: expect.any(Object),
            instituicaoEnsino: expect.any(Object),
          }),
        );
      });

      it('passing IContratoFuncionario should create a new form with FormGroup', () => {
        const formGroup = service.createContratoFuncionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            salarioFixo: expect.any(Object),
            salarioVariavel: expect.any(Object),
            estagio: expect.any(Object),
            naturezaEstagioEnum: expect.any(Object),
            ctps: expect.any(Object),
            serieCtps: expect.any(Object),
            orgaoEmissorDocumento: expect.any(Object),
            dataValidadeDocumento: expect.any(Object),
            dataAdmissao: expect.any(Object),
            cargo: expect.any(Object),
            descricaoAtividades: expect.any(Object),
            situacao: expect.any(Object),
            valorSalarioFixo: expect.any(Object),
            valorSalarioVariavel: expect.any(Object),
            dataTerminoContrato: expect.any(Object),
            datainicioContrato: expect.any(Object),
            horasATrabalhadar: expect.any(Object),
            codigoCargo: expect.any(Object),
            categoriaTrabalhador: expect.any(Object),
            tipoVinculoTrabalho: expect.any(Object),
            fgtsOpcao: expect.any(Object),
            tIpoDocumentoEnum: expect.any(Object),
            periodoExperiencia: expect.any(Object),
            tipoAdmisaoEnum: expect.any(Object),
            periodoIntermitente: expect.any(Object),
            indicativoAdmissao: expect.any(Object),
            numeroPisNisPasep: expect.any(Object),
            funcionario: expect.any(Object),
            agenteIntegracaoEstagio: expect.any(Object),
            instituicaoEnsino: expect.any(Object),
          }),
        );
      });
    });

    describe('getContratoFuncionario', () => {
      it('should return NewContratoFuncionario for default ContratoFuncionario initial value', () => {
        const formGroup = service.createContratoFuncionarioFormGroup(sampleWithNewData);

        const contratoFuncionario = service.getContratoFuncionario(formGroup) as any;

        expect(contratoFuncionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewContratoFuncionario for empty ContratoFuncionario initial value', () => {
        const formGroup = service.createContratoFuncionarioFormGroup();

        const contratoFuncionario = service.getContratoFuncionario(formGroup) as any;

        expect(contratoFuncionario).toMatchObject({});
      });

      it('should return IContratoFuncionario', () => {
        const formGroup = service.createContratoFuncionarioFormGroup(sampleWithRequiredData);

        const contratoFuncionario = service.getContratoFuncionario(formGroup) as any;

        expect(contratoFuncionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContratoFuncionario should not enable id FormControl', () => {
        const formGroup = service.createContratoFuncionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContratoFuncionario should disable id FormControl', () => {
        const formGroup = service.createContratoFuncionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
