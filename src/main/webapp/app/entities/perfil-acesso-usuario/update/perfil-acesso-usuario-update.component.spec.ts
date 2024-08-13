import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PerfilAcessoUsuarioService } from '../service/perfil-acesso-usuario.service';
import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';
import { PerfilAcessoUsuarioFormService } from './perfil-acesso-usuario-form.service';

import { PerfilAcessoUsuarioUpdateComponent } from './perfil-acesso-usuario-update.component';

describe('PerfilAcessoUsuario Management Update Component', () => {
  let comp: PerfilAcessoUsuarioUpdateComponent;
  let fixture: ComponentFixture<PerfilAcessoUsuarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let perfilAcessoUsuarioFormService: PerfilAcessoUsuarioFormService;
  let perfilAcessoUsuarioService: PerfilAcessoUsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PerfilAcessoUsuarioUpdateComponent],
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
      .overrideTemplate(PerfilAcessoUsuarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PerfilAcessoUsuarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    perfilAcessoUsuarioFormService = TestBed.inject(PerfilAcessoUsuarioFormService);
    perfilAcessoUsuarioService = TestBed.inject(PerfilAcessoUsuarioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const perfilAcessoUsuario: IPerfilAcessoUsuario = { id: 456 };

      activatedRoute.data = of({ perfilAcessoUsuario });
      comp.ngOnInit();

      expect(comp.perfilAcessoUsuario).toEqual(perfilAcessoUsuario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilAcessoUsuario>>();
      const perfilAcessoUsuario = { id: 123 };
      jest.spyOn(perfilAcessoUsuarioFormService, 'getPerfilAcessoUsuario').mockReturnValue(perfilAcessoUsuario);
      jest.spyOn(perfilAcessoUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilAcessoUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilAcessoUsuario }));
      saveSubject.complete();

      // THEN
      expect(perfilAcessoUsuarioFormService.getPerfilAcessoUsuario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(perfilAcessoUsuarioService.update).toHaveBeenCalledWith(expect.objectContaining(perfilAcessoUsuario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilAcessoUsuario>>();
      const perfilAcessoUsuario = { id: 123 };
      jest.spyOn(perfilAcessoUsuarioFormService, 'getPerfilAcessoUsuario').mockReturnValue({ id: null });
      jest.spyOn(perfilAcessoUsuarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilAcessoUsuario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: perfilAcessoUsuario }));
      saveSubject.complete();

      // THEN
      expect(perfilAcessoUsuarioFormService.getPerfilAcessoUsuario).toHaveBeenCalled();
      expect(perfilAcessoUsuarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPerfilAcessoUsuario>>();
      const perfilAcessoUsuario = { id: 123 };
      jest.spyOn(perfilAcessoUsuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ perfilAcessoUsuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(perfilAcessoUsuarioService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
