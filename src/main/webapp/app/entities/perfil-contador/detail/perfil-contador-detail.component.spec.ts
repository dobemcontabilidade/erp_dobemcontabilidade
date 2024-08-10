import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PerfilContadorDetailComponent } from './perfil-contador-detail.component';

describe('PerfilContador Management Detail Component', () => {
  let comp: PerfilContadorDetailComponent;
  let fixture: ComponentFixture<PerfilContadorDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilContadorDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PerfilContadorDetailComponent,
              resolve: { perfilContador: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PerfilContadorDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PerfilContadorDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load perfilContador on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PerfilContadorDetailComponent);

      // THEN
      expect(instance.perfilContador()).toEqual(expect.objectContaining({ id: 123 }));
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
