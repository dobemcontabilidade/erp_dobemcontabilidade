import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PerfilRedeSocialDetailComponent } from './perfil-rede-social-detail.component';

describe('PerfilRedeSocial Management Detail Component', () => {
  let comp: PerfilRedeSocialDetailComponent;
  let fixture: ComponentFixture<PerfilRedeSocialDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilRedeSocialDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PerfilRedeSocialDetailComponent,
              resolve: { perfilRedeSocial: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PerfilRedeSocialDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilRedeSocialDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load perfilRedeSocial on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PerfilRedeSocialDetailComponent);

      // THEN
      expect(instance.perfilRedeSocial()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
