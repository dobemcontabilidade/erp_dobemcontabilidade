import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { UsuarioContadorService } from 'app/entities/usuario-contador/service/usuario-contador.service';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { PerfilContadorService } from 'app/entities/perfil-contador/service/perfil-contador.service';
import { IContador } from '../contador.model';
import { ContadorService } from '../service/contador.service';
import { ContadorFormService } from './contador-form.service';

import { ContadorUpdateComponent } from './contador-update.component';

describe('Contador Management Update Component', () => {
  let comp: ContadorUpdateComponent;
  let fixture: ComponentFixture<ContadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contadorFormService: ContadorFormService;
  let contadorService: ContadorService;
  let pessoaService: PessoaService;
  let usuarioContadorService: UsuarioContadorService;
  let perfilContadorService: PerfilContadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContadorUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contadorFormService = TestBed.inject(ContadorFormService);
    contadorService = TestBed.inject(ContadorService);
    pessoaService = TestBed.inject(PessoaService);
    usuarioContadorService = TestBed.inject(UsuarioContadorService);
    perfilContadorService = TestBed.inject(PerfilContadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoa query and add missing value', () => {
      const contador: IContador = { id: 456 };
      const pessoa: IPessoa = { id: 29372 };
      contador.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 6189 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const expectedCollection: IPessoa[] = [pessoa, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(pessoaCollection, pessoa);
      expect(comp.pessoasCollection).toEqual(expectedCollection);
    });

    it('Should call usuarioContador query and add missing value', () => {
      const contador: IContador = { id: 456 };
      const usuarioContador: IUsuarioContador = { id: 10457 };
      contador.usuarioContador = usuarioContador;

      const usuarioContadorCollection: IUsuarioContador[] = [{ id: 28973 }];
      jest.spyOn(usuarioContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: usuarioContadorCollection })));
      const expectedCollection: IUsuarioContador[] = [usuarioContador, ...usuarioContadorCollection];
      jest.spyOn(usuarioContadorService, 'addUsuarioContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      expect(usuarioContadorService.query).toHaveBeenCalled();
      expect(usuarioContadorService.addUsuarioContadorToCollectionIfMissing).toHaveBeenCalledWith(
        usuarioContadorCollection,
        usuarioContador,
      );
      expect(comp.usuarioContadorsCollection).toEqual(expectedCollection);
    });

    it('Should call PerfilContador query and add missing value', () => {
      const contador: IContador = { id: 456 };
      const perfilContador: IPerfilContador = { id: 8600 };
      contador.perfilContador = perfilContador;

      const perfilContadorCollection: IPerfilContador[] = [{ id: 1499 }];
      jest.spyOn(perfilContadorService, 'query').mockReturnValue(of(new HttpResponse({ body: perfilContadorCollection })));
      const additionalPerfilContadors = [perfilContador];
      const expectedCollection: IPerfilContador[] = [...additionalPerfilContadors, ...perfilContadorCollection];
      jest.spyOn(perfilContadorService, 'addPerfilContadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      expect(perfilContadorService.query).toHaveBeenCalled();
      expect(perfilContadorService.addPerfilContadorToCollectionIfMissing).toHaveBeenCalledWith(
        perfilContadorCollection,
        ...additionalPerfilContadors.map(expect.objectContaining),
      );
      expect(comp.perfilContadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contador: IContador = { id: 456 };
      const pessoa: IPessoa = { id: 6976 };
      contador.pessoa = pessoa;
      const usuarioContador: IUsuarioContador = { id: 10484 };
      contador.usuarioContador = usuarioContador;
      const perfilContador: IPerfilContador = { id: 1259 };
      contador.perfilContador = perfilContador;

      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      expect(comp.pessoasCollection).toContain(pessoa);
      expect(comp.usuarioContadorsCollection).toContain(usuarioContador);
      expect(comp.perfilContadorsSharedCollection).toContain(perfilContador);
      expect(comp.contador).toEqual(contador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContador>>();
      const contador = { id: 123 };
      jest.spyOn(contadorFormService, 'getContador').mockReturnValue(contador);
      jest.spyOn(contadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contador }));
      saveSubject.complete();

      // THEN
      expect(contadorFormService.getContador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contadorService.update).toHaveBeenCalledWith(expect.objectContaining(contador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContador>>();
      const contador = { id: 123 };
      jest.spyOn(contadorFormService, 'getContador').mockReturnValue({ id: null });
      jest.spyOn(contadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contador }));
      saveSubject.complete();

      // THEN
      expect(contadorFormService.getContador).toHaveBeenCalled();
      expect(contadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContador>>();
      const contador = { id: 123 };
      jest.spyOn(contadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUsuarioContador', () => {
      it('Should forward to usuarioContadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(usuarioContadorService, 'compareUsuarioContador');
        comp.compareUsuarioContador(entity, entity2);
        expect(usuarioContadorService.compareUsuarioContador).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePerfilContador', () => {
      it('Should forward to perfilContadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(perfilContadorService, 'comparePerfilContador');
        comp.comparePerfilContador(entity, entity2);
        expect(perfilContadorService.comparePerfilContador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
